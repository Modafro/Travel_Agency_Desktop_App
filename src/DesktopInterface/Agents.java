//Objective: Create an agent class
package DesktopInterface;

public class Agents {
    private int AgentId;
    private String AgtFirstName;
    private String AgtMiddleInitial;
    private String AgtLastName;
    private String AgtBusPhone;
    private String AgtEmail;
    private String AgtPosition;
    private int AgencyId;
    private String AgtPassword;
    private String AgtUserName;

    public  Agents() {}

    public Agents(int agentId, String agtFirstName, String agtMiddleInitial, String agtLastName, String agtBusPhone,
                  String agtEmail, String agtPosition, int agencyId, String agtPassword, String agtUserName) {
        this.AgentId = agentId;
        this.AgtFirstName = agtFirstName;
        this.AgtMiddleInitial = agtMiddleInitial;
        this.AgtLastName = agtLastName;
        this.AgtBusPhone = agtBusPhone;
        this.AgtEmail = agtEmail;
        this.AgtPosition = agtPosition;
        this.AgencyId = agencyId;
        this.AgtPassword = agtPassword;
        this.AgtUserName = agtUserName;
    }

    public Agents(String agtPassword, String agtUserName) {
        this.AgtPassword = agtPassword;
        this.AgtUserName = agtUserName;
    }

    public int getAgentId() {
        return AgentId;
    }

    public void setAgentId(int agentId) {
        AgentId = agentId;
    }

    public String getAgtFirstName() {
        return AgtFirstName;
    }

    public void setAgtFirstName(String agtFirstName) {
        AgtFirstName = agtFirstName;
    }

    public String getAgtMiddleInitial() {
        return AgtMiddleInitial;
    }

    public void setAgtMiddleInitial(String agtMiddleInitial) {
        AgtMiddleInitial = agtMiddleInitial;
    }

    public String getAgtLastName() {
        return AgtLastName;
    }

    public void setAgtLastName(String agtLastName) {
        AgtLastName = agtLastName;
    }

    public String getAgtBusPhone() {
        return AgtBusPhone;
    }

    public void setAgtBusPhone(String agtBusPhone) {
        AgtBusPhone = agtBusPhone;
    }

    public String getAgtEmail() {
        return AgtEmail;
    }

    public void setAgtEmail(String agtEmail) {
        AgtEmail = agtEmail;
    }

    public String getAgtPosition() {
        return AgtPosition;
    }

    public void setAgtPosition(String agtPosition) {
        AgtPosition = agtPosition;
    }

    public int getAgencyId() {
        return AgencyId;
    }

    public void setAgencyId(int agencyId) {
        AgencyId = agencyId;
    }

    public String getAgtPassword() {
        return AgtPassword;
    }

    public void setAgtPassword(String agtPassword) {
        AgtPassword = agtPassword;
    }

    public String getAgtUserName() {
        return AgtUserName;
    }

    public void setAgtUserName(String agtUserName) {
        AgtUserName = agtUserName;
    }
}
