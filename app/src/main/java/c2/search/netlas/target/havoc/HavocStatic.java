package c2.search.netlas.target.havoc;

import c2.search.netlas.analyze.StaticData;
import c2.search.netlas.annotation.Static;
import java.util.List;
import netlas.java.scheme.Certificate;
import netlas.java.scheme.Headers;

@Static(name = "Havoc")
public class HavocStatic implements StaticData {
  public HavocStatic() {}

  @Override
  public List<String> getJarm() {
    return List.of("3fd21b20d00000021c43d21b21b43de0a012c76cf078b8d06f4620c2286f5e");
  }

  @Override
  public List<Certificate> getCertificate() {
    return List.of();
  }

  @Override
  public List<Integer> getPort() {
    return List.of();
  }

  @Override
  public List<Headers> getHeader() {
    final Headers headers = new Headers();
    headers.setHeader("x-ishavocframework", null);
    return List.of(headers);
  }

  @Override
  public List<String> getBodyAsSha256() {
    return List.of("b16e15764b8bc06c5c3f9f19bc8b99fa48e7894aa5a6ccdad65da49bbf564793");
  }
}
