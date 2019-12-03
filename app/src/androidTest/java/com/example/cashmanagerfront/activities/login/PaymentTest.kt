package com.example.cashmanagerfront.activities.login


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.example.cashmanagerfront.R
import com.example.cashmanagerfront.activities.ProductList
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PaymentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)


    @Test
    fun paymentTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.username),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.usernameLayout),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(scrollTo(), replaceText("bloodbee631@gmail.com"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.passwordLayout),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText2.perform(scrollTo(), replaceText("aaaaaa"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.login), withText("Sign in"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val button = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        0
                    ),
                    2
                )
            )
        )
        button.perform(scrollTo(), click())

        val button2 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        1
                    ),
                    2
                )
            )
        )
        button2.perform(scrollTo(), click())

        val button3 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        1
                    ),
                    2
                )
            )
        )
        button3.perform(scrollTo(), click())

        val button4 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        2
                    ),
                    2
                )
            )
        )
        button4.perform(scrollTo(), click())

        val button5 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        2
                    ),
                    2
                )
            )
        )
        button5.perform(scrollTo(), click())

        val button6 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        2
                    ),
                    2
                )
            )
        )
        button6.perform(scrollTo(), click())

        val textView = onView(
            allOf(
                withText("1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("1")))

        val textView2 = onView(
            allOf(
                withText("1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("1")))

        val textView3 = onView(
            allOf(
                withText("2"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("2")))

        val textView4 = onView(
            allOf(
                withText("3"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("3")))

        val textView5 = onView(
            allOf(
                withText("0"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        3
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("0")))

        val materialButton2 = onView(
            allOf(
                withId(R.id.productListButtonCheckCart), withText("Check cart"),
                childAtPosition(
                    allOf(
                        withId(R.id.productListMainLayout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val textView6 = onView(
            allOf(
                withText("7200.00 $"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.resumeCartScrollLinearLayout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("7200.00 $")))

        val textView7 = onView(
            allOf(
                withText("3"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("3")))

        val textView8 = onView(
            allOf(
                withText("2.40 $"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.resumeCartScrollLinearLayout),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("2.40 $")))

        val textView9 = onView(
            allOf(
                withText("2"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("2")))

        val textView10 = onView(
            allOf(
                withText("800.00 $"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.resumeCartScrollLinearLayout),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView10.check(matches(withText("800.00 $")))

        val textView11 = onView(
            allOf(
                withText("1"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView11.check(matches(withText("1")))

        val editText = onView(
            allOf(
                withId(R.id.resumeCartTableRow1TotalHTNumber),
                isDisplayed()
            )
        )
        editText.check(matches(withText("8002.40")))

        val editText2 = onView(
            allOf(
                withId(R.id.resumeCartTableRow2TotalTTCNumber),
                isDisplayed()
            )
        )
        editText2.check(matches(withText("9602.88")))

        val button7 = onView(
            allOf(
                withText("-"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.resumeCartScrollLinearLayout),
                        0
                    ),
                    2
                )
            )
        )
        button7.perform(scrollTo(), click())

        val button8 = onView(
            allOf(
                withText("-"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.resumeCartScrollLinearLayout),
                        1
                    ),
                    2
                )
            )
        )
        button8.perform(scrollTo(), click())

        val editText3 = onView(
            allOf(
                withId(R.id.resumeCartTableRow1TotalHTNumber),
                isDisplayed()
            )
        )
        editText3.check(matches(withText("5601.20")))

        val editText4 = onView(
            allOf(
                withId(R.id.resumeCartTableRow2TotalTTCNumber),
                isDisplayed()
            )
        )
        editText4.check(matches(withText("6721.44")))

        val materialButton3 = onView(
            allOf(
                withId(R.id.resumeCartButtonEmpty), withText("Empty cart"),
                childAtPosition(
                    allOf(
                        withId(R.id.resumeCartMainLayout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val editText5 = onView(
            allOf(
                withId(R.id.resumeCartTableRow2TotalTTCNumber),
                isDisplayed()
            )
        )
        editText5.check(matches(withText("0.00")))

        val materialButton4 = onView(
            allOf(
                withId(R.id.resumeCartButtonBack), withText("Go back to products"),
                childAtPosition(
                    allOf(
                        withId(R.id.resumeCartMainLayout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val button9 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        0
                    ),
                    2
                )
            )
        )
        button9.perform(scrollTo(), click())

        val button10 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        0
                    ),
                    2
                )
            )
        )
        button10.perform(scrollTo(), click())

        val button11 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        1
                    ),
                    2
                )
            )
        )
        button11.perform(scrollTo(), click())

        val button12 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        0
                    ),
                    2
                )
            )
        )
        button12.perform(scrollTo(), click())

        val button13 = onView(
            allOf(
                withText("+"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.productListScrollLinearLayout),
                        2
                    ),
                    2
                )
            )
        )
        button13.perform(scrollTo(), click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.productListButtonCheckCart), withText("Check cart"),
                childAtPosition(
                    allOf(
                        withId(R.id.productListMainLayout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.resumeCartButtonPayment), withText("Proceed to payment"),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val textView14 = onView(
            allOf(
                withId(R.id.paymentStatusText),
                isDisplayed()
            )
        )
        textView14.check(matches(withText("Payment status : No payment done")))

        val imageView = onView(
            allOf(
                withId(R.id.paymentQrCodeImageView),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
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
