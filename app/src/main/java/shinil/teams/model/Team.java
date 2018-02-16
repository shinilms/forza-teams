package shinil.teams.model;

/**
 * @author shinilms
 */

public final class Team {
    private String name;
    private boolean national;
    private String country_name;

    public Team(String name, boolean national, String country_name) {
        this.name = name;
        this.national = national;
        this.country_name = country_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNational() {
        return national;
    }

    @SuppressWarnings("unused")
    public void setNational(boolean national) {
        this.national = national;
    }

    public String getCountry_name() {
        return country_name;
    }

    @SuppressWarnings("unused")
    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}
