package com.appdynamics.pmdemoapps.android.cart.test;

import com.appdynamics.pmdemoapps.android.cart.EntryActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class AcmeRobotiumCheckout extends ActivityInstrumentationTestCase2<EntryActivity> {
  	private Solo solo;

  	public AcmeRobotiumCheckout() {
		super(EntryActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}

    public void testRun() {
        doLogin();

        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));

        int noOfBooks = randInt(1,8);
        ArrayList<Integer> shuffled = shuffleItems(0,noOfBooks);
        for (int i = 0; i < noOfBooks; i++) {
            doAddBook(shuffled.get(i));
        }

        doCheckout();
    }

    private ArrayList<Integer> shuffleItems(int min, int max) {
        ArrayList<Integer> elements = new ArrayList<Integer>();
        for (int i = min; i < max; i++) {
            elements.add(i, i);
        }
        Collections.shuffle(elements);
        return elements;
    }

    private void doLogin () {
        solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.EntryActivity.class, 2000);
        Timeout.setSmallTimeout(101011);
        solo.clearEditText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.username));
        solo.enterText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.username), "test");

        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.password));
        solo.clearEditText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.password));
        solo.enterText((android.widget.EditText) solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.password), "appdynamics");

        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.sign_in_button));
    }

    private void doCheckout() {
        solo.clickOnText(java.util.regex.Pattern.quote("Cart"));
        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.button2));
        solo.clickOnText(java.util.regex.Pattern.quote("List"));
    }

    private void doAddBook (int book) {
        solo.clickInList(book, 0);
        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemDetailActivity.class));
        solo.clickOnView(solo.getView(com.appdynamics.pmdemoapps.android.cart.R.id.oops_button));
        assertTrue("com.appdynamics.pmdemoapps.android.cart.ItemListActivity is not found!", solo.waitForActivity(com.appdynamics.pmdemoapps.android.cart.ItemListActivity.class));
    }

    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
