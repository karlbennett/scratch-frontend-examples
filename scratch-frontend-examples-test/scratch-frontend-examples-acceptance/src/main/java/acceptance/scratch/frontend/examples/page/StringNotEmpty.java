package acceptance.scratch.frontend.examples.page;

import shiver.me.timbers.waiting.ResultValidator;

public class StringNotEmpty implements ResultValidator<String> {

    @Override
    public boolean isValid(String string) throws Throwable {
        return string != null && !string.isEmpty();
    }
}
