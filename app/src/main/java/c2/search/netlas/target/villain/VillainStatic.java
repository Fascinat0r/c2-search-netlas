package c2.search.netlas.target.villain;

import c2.search.netlas.analyze.StaticData;
import c2.search.netlas.annotation.Detect;
import java.util.Arrays;
import java.util.List;
import netlas.java.scheme.Certificate;
import netlas.java.scheme.Headers;
import netlas.java.scheme.Issuer;
import netlas.java.scheme.Subject;

@Detect(name = "Villain")
public class VillainStatic implements StaticData {

  @Override
  public List<String> getJarm() {
    final String jarm = "2ad2ad0002ad2ad22c42d42d000000faabb8fd156aa8b4d8a37853e1063261";
    return Arrays.asList(jarm);
  }

  @Override
  public List<Certificate> getCertificate() {
    // * subject: C=AU; ST=Some-State; O=Internet Widgits Pty Ltd
    // * issuer: C=AU; ST=Some-State; O=Internet Widgits Pty Ltd
    Certificate certificate = new Certificate();
    Subject subject = new Subject();
    var country = Arrays.asList("AU");
    var state = Arrays.asList("Some-State");
    var organization = Arrays.asList("Internet Widgits Pty Ltd");
    subject.setCountry(country);
    subject.setLocality(state);
    subject.setOrganization(organization);
    Issuer issuer = new Issuer();
    issuer.setCountry(country);
    issuer.setLocality(state);
    issuer.setOrganization(organization);
    certificate.setIssuer(issuer);
    certificate.setSubject(subject);
    return Arrays.asList(certificate);
  }

  @Override
  public List<Integer> getPort() {
    return null;
  }

  @Override
  public List<Headers> getHeader() {
    Headers headers = new Headers();
    headers.setHeader("server", List.of("BaseHTTP"));
    return Arrays.asList(headers);
  }

  @Override
  public List<String> getBodyAsSha256() {
    return null;
  }
}
