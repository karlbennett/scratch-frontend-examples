package cucumber.scratch.frontend.examples.step;

import org.springframework.context.annotation.Scope;

@Scope("cucumber-glue")
public class GenericHolder<T> {

    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
