package ruleset1

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.config
import io.gitlab.arturbosch.detekt.api.internal.Configuration
import org.jetbrains.kotlin.psi.KtNamedFunction

/**
 * Description
 */
class CognitiveComplexMethod(config: Config = Config.empty) : Rule(config) {

    override val issue = Issue(
        "CognitiveComplexMethod",
        "Prefer splitting up complex methods into smaller, easier to understand methods.",
        Debt.TWENTY_MINS
    )

    @Configuration("Maximum Cognitive Complexity allowed for a method.")
    private val allowedComplexity: Int by config(defaultValue = 15)

    @SuppressWarnings("EmptyFunctionBlock")
    override fun visitNamedFunction(function: KtNamedFunction) {
        // This is just for testing purpose
    }
}
