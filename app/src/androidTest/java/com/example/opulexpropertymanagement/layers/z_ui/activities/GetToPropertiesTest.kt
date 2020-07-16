package com.example.opulexpropertymanagement.layers.z_ui.activities


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.opulexpropertymanagement.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class GetToPropertiesTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(ActivityHost::class.java)

    @Test
    fun getToPropertiesTest() {
        val cardView = onView(
            allOf(
                withId(R.id.cardview_landlord),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragNavHost),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        cardView.perform(click())

        val button = onView(
            allOf(
                withId(R.id.btn_add_property),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragNavHost),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
