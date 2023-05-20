package c2.search.netlas.target.phoenix;

import c2.search.netlas.analyze.StaticData;
import c2.search.netlas.annotation.Static;
import java.util.Arrays;
import java.util.List;
import netlas.java.scheme.Certificate;
import netlas.java.scheme.Headers;
import netlas.java.scheme.Issuer;
import netlas.java.scheme.Subject;

@Static(name = "Phoenix")
public class PhoenixStatic implements StaticData {
  public PhoenixStatic() {}

  @Override
  public List<String> getJarm() {
    final String jarm = "2ad2ad0002ad2ad22c42d42d000000faabb8fd156aa8b4d8a37853e1063261";
    return Arrays.asList(jarm);
  }

  @Override
  public List<Certificate> getCertificate() {
    final Certificate certificate = new Certificate();
    final Subject subject = new Subject();
    final List<String> country = Arrays.asList("US");
    subject.setCountry(country);
    certificate.setSubject(subject);
    final Issuer issuer = new Issuer();
    issuer.setCountry(country);
    certificate.setIssuer(issuer);
    return Arrays.asList(certificate);
  }

  @Override
  public List<Integer> getPort() {
    return List.of();
  }

  @Override
  public List<Headers> getHeader() {
    final Headers headers = new Headers();
    headers.setHeader("content-type", List.of("text/html; charset=utf-8"));
    headers.setHeader("server", List.of("Werkzeug"));
    return Arrays.asList(headers);
  }

  @Override
  public List<String> getBodyAsSha256() {
    return Arrays.asList(
        "bdd3acd38b235f3e79f97834c1bada34fe87489f5cc3c530dab5bc47404e0a87",
        "e9639e3c4681ce85f852fbac48e2eeee5ba51296dbfec57c200d59b76237ab80");
  }
}
