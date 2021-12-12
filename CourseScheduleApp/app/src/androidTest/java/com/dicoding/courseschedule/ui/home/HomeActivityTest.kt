import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

	@get:Rule
	var mActivityTestRule = ActivityScenarioRule(HomeActivity::class.java)

	@Test
	fun homeActivityTest() {
		onView(withId(R.id.action_add)).perform(click())
		onView(withText("Add Course")).check(matches(isDisplayed()))
	}
}
