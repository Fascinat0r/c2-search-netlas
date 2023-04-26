package c2.search.netlas.target.bruteratel;

import c2.search.netlas.analyze.StaticData;
import c2.search.netlas.annotation.Detect;
import java.util.List;
import netlas.java.scheme.Certificate;
import netlas.java.scheme.Headers;
import netlas.java.scheme.Issuer;
import netlas.java.scheme.Subject;

@Detect(name = "Bruteratel")
class BruteratelStatic implements StaticData {
  @Override
  public List<String> getJarm() {
    return List.of("3fd21b20d00000021c43d21b21b43de0a012c76cf078b8d06f4620c2286f5e");
  }

  @Override
  public List<Certificate> getCertificate() {
    Certificate certificate = new Certificate();
    Subject subject = new Subject();
    subject.setCountry(List.of("US"));
    subject.setLocality(List.of("California"));
    subject.setOrganization(List.of("Microsoft"));
    subject.setOrganizationalUnit(List.of("Security"));
    subject.setCommonName(List.of("localhost"));
    Issuer issuer = new Issuer();
    issuer.setCountry(List.of("US"));
    issuer.setLocality(List.of("California"));
    issuer.setOrganization(List.of("Microsoft"));
    issuer.setOrganizationalUnit(List.of("Security"));
    issuer.setCommonName(List.of("localhost"));

    certificate.setIssuer(issuer);
    certificate.setSubject(subject);

    return List.of(certificate);
  }

  @Override
  public List<Integer> getPort() {
    return null;
  }

  @Override
  public List<Headers> getHeader() {
    return null;
  }

  @Override
  public List<String> getBodyAsSha256() {
    return null;
  }
}
