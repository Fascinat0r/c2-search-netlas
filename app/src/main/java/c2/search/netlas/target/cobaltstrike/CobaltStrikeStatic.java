package c2.search.netlas.target.cobaltstrike;

import c2.search.netlas.analyze.StaticData;
import c2.search.netlas.annotation.Static;
import java.util.ArrayList;
import java.util.List;
import netlas.java.scheme.Certificate;
import netlas.java.scheme.Headers;
import netlas.java.scheme.Issuer;
import netlas.java.scheme.Subject;

@Static(name = "CobaltStrike")
public class CobaltStrikeStatic implements StaticData {
  public CobaltStrikeStatic() {}

  @Override
  public List<String> getJarm() {
    return List.of("2ad2ad16d2ad2ad00042d42d00042ddb04deffa1705e2edc44cae1ed24a4da");
  }

  private Certificate generateCert(
      final String country,
      final String province,
      final String locality,
      final String organization,
      final String unit,
      final String commonName) {

    final Subject subject = new Subject();
    subject.setCountry(List.of(country));
    subject.setProvince(List.of(province));
    subject.setLocality(List.of(locality));
    subject.setOrganization(List.of(organization));
    subject.setOrganizationalUnit(List.of(unit));
    subject.setCommonName(List.of(commonName));

    final Issuer issuer = new Issuer();
    issuer.setCountry(List.of(country));
    issuer.setLocality(List.of(locality));
    issuer.setOrganization(List.of(organization));
    issuer.setOrganizationalUnit(List.of(unit));
    issuer.setCommonName(List.of(commonName));

    final Certificate cert = new Certificate();
    cert.setIssuer(issuer);
    cert.setSubject(subject);

    return cert;
  }

  @Override
  public List<Certificate> getCertificate() {
    final Certificate teamserver =
        generateCert(
            "US",
            "Washington",
            "Redmond",
            "Microsoft Corporation",
            "Microsoft Corporation",
            "Outlook.live.com");
    final Certificate listener = generateCert("", "", "", "", "", "");
    return List.of(teamserver, listener);
  }

  @Override
  public List<Integer> getPort() {
    final int defaultPort = 41337;
    return List.of(defaultPort);
  }

  @Override
  public List<Headers> getHeader() {
    final Headers headers = new Headers();
    headers.setHeader("server", new ArrayList<>());
    headers.setHeader("content-length", List.of("0"));
    headers.setHeader("content-type", List.of("text/plain"));
    return List.of(headers);
  }

  @Override
  public List<String> getBodyAsSha256() {
    return List.of();
  }
}
