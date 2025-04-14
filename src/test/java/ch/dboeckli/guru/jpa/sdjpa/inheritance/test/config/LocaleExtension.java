package ch.dboeckli.guru.jpa.sdjpa.inheritance.test.config;

import java.util.Locale;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LocaleExtension implements BeforeAllCallback {
  @Override
  public void beforeAll(ExtensionContext context) {
    Locale.setDefault(Locale.US);
  }
}
