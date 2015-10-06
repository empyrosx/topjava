package ru.javawebinar.topjava.matcher;

import org.hamcrest.*;

public class CauseMatcher extends DiagnosingMatcher<Throwable> {

    private final Class<? extends Throwable> expectedCause;

    public CauseMatcher(Class<? extends Throwable> expectedCause) {
        this.expectedCause = expectedCause;
    }

    public void describeTo(Description description) {
        description.appendText("exception with cause ");
        description.appendText(expectedCause.getName());
    }

    protected boolean matches(Object o, Description mismatch) {
        if (null == o) {
            mismatch.appendText("null");
            return false;
        }

        Throwable cause = getCause((Throwable) o);
        if (!this.expectedCause.isInstance(cause)) {
            mismatch.appendValue(cause).appendText(" is a " + cause.getClass().getName());
            return false;
        } else {
            return true;
        }
    }

    private Throwable getCause(Throwable th) {
        Throwable result = th;
        while (result.getCause() != null) {
            result = result.getCause();
        }
        return result;
    }
}