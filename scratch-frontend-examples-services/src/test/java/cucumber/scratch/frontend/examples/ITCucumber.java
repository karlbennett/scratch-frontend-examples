package cucumber.scratch.frontend.examples;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"cucumber", "scratch.frontend.examples"})
@RunWith(Cucumber.class)
@CucumberOptions(
    // The "glue" and "features" settings are redundant because the values for these are, by default, set to the
    // location of the class annotated with "@RunWith(Cucumber.class)". I am just setting them here to give an example
    // of their usage.
    glue = {"cucumber.scratch.frontend.examples"},
    features = "classpath:cucumber/scratch/frontend/examples",
    format = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber-json-report.json"}
)

public class ITCucumber {
}
