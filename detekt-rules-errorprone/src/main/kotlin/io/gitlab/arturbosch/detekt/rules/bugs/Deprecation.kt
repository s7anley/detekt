package io.gitlab.arturbosch.detekt.rules.bugs

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.internal.RequiresTypeResolution
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.diagnostics.Errors
import org.jetbrains.kotlin.psi.KtNamedDeclaration

/**
 * Deprecated elements are expected to be removed in the future. Alternatives should be found if possible.
 *
 */
@RequiresTypeResolution
class Deprecation(config: Config) : Rule(config) {

    override val issue = Issue(
        "Deprecation",
        "Deprecated elements should not be used.",
        Debt.TWENTY_MINS
    )

    override val defaultRuleIdAliases = setOf("DEPRECATION")

    override fun visitElement(element: PsiElement) {
        if (hasDeprecationCompilerWarnings(element)) {
            val entity = if (element is KtNamedDeclaration) Entity.atName(element) else Entity.from(element)
            report(CodeSmell(issue, entity, "${element.text} is deprecated."))
        }
        super.visitElement(element)
    }

    private fun hasDeprecationCompilerWarnings(element: PsiElement) =
        bindingContext.diagnostics
            .forElement(element)
            .any { it.factory == Errors.DEPRECATION }
}
