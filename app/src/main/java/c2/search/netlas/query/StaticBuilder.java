package c2.search.netlas.query;

import c2.search.netlas.analyze.StaticData;
import java.util.StringJoiner;
import netlas.java.scheme.Certificate;
import netlas.java.scheme.Headers;

public class StaticBuilder implements QueryBuilder {
  private final StaticData data;
  private String separator;

  public StaticBuilder(final StaticData data) {
    this.data = data;
    this.separator = " OR ";
  }

  @Override
  public String build() {
    return generate(data).toString();
  }

  @Override
  public void setSeparator(final String separator) {
    this.separator = separator;
  }

  @Override
  public String getSeparator() {
    return separator;
  }

  private StringJoiner generate(final StaticData data) {
    final StringJoiner joiner = new StringJoiner(separator);
    if (data == null) {
      return joiner;
    }

    QueryBuilder builder = new JarmBuilder(data.getJarm());
    joiner.add(builder.build());

    builder = new PortBuilder(data.getPort());
    joiner.add(builder.build());

    builder = new BodyAsSha256Builder(data.getBodyAsSha256());
    joiner.add(builder.build());

    for (final Certificate certificate : data.getCertificate()) {
      builder = new CertificateBuilder(certificate);
      joiner.add(builder.build());
    }
    for (final Headers header : data.getHeader()) {
      builder = new HeaderBuilder(header);
      joiner.add(builder.build());
    }

    return joiner;
  }
}
