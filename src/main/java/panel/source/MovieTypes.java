package panel.source;

public class MovieTypes {
  private boolean isNovel;
  private boolean isRomance;
  private boolean isHorror;
  private boolean isComedy;
  private boolean isScience;

  // Default constructor
  public MovieTypes() {
    // All fields default to false
  }

  // Parameterized constructor
  public MovieTypes(boolean isNovel, boolean isRomance, boolean isHorror,
      boolean isComedy, boolean isScience) {
    this.isNovel = isNovel;
    this.isRomance = isRomance;
    this.isHorror = isHorror;
    this.isComedy = isComedy;
    this.isScience = isScience;
  }

  // Getters and setters
  public boolean isNovel() {
    return isNovel;
  }

  public void setNovel(boolean novel) {
    isNovel = novel;
  }

  public boolean isRomance() {
    return isRomance;
  }

  public void setRomance(boolean romance) {
    isRomance = romance;
  }

  public boolean isHorror() {
    return isHorror;
  }

  public void setHorror(boolean horror) {
    isHorror = horror;
  }

  public boolean isComedy() {
    return isComedy;
  }

  public void setComedy(boolean comedy) {
    isComedy = comedy;
  }

  public boolean isScience() {
    return isScience;
  }

  public void setScience(boolean science) {
    isScience = science;
  }

  @Override
  public String toString() {
    return "MovieTypes{" +
        "isNovel=" + isNovel +
        ", isRomance=" + isRomance +
        ", isHorror=" + isHorror +
        ", isComedy=" + isComedy +
        ", isScience=" + isScience +
        '}';
  }
}