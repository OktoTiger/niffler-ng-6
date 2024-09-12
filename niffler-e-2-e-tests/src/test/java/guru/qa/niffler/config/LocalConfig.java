package guru.qa.niffler.config;

enum LocalConfig implements Config {
  INSTANCE;

  @Override
  public String frontUrl() {
    return "http://127.0.0.1:3000/";
  }

  public String registerUrl() {
    return "http://127.0.0.1:9000/register";
  }

  @Override
  public String spendUrl() {
    return "http://127.0.0.1:8093/";
  }
}
