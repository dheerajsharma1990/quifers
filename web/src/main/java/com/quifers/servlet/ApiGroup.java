package com.quifers.servlet;

public class ApiGroup {

    public static final ApiGroup GUEST = new ApiGroup("/api/v0/guest");
    public static final ApiGroup ADMIN = new ApiGroup("/api/v0/admin");
    public static final ApiGroup FIELD_EXECUTIVE = new ApiGroup("/api/v0/executive");

    private final String path;

    public ApiGroup(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static ApiGroup getMatchingApiGroup(String requestUri) throws CommandNotFoundException {
        if (requestUri.startsWith(GUEST.path)) {
            return GUEST;
        } else if (requestUri.startsWith(ADMIN.path)) {
            return ADMIN;
        } else if (requestUri.startsWith(FIELD_EXECUTIVE.path)) {
            return FIELD_EXECUTIVE;
        }
        throw new CommandNotFoundException("No Api Group defined for request [" + requestUri + "].");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiGroup apiGroup = (ApiGroup) o;

        if (path != null ? !path.equals(apiGroup.path) : apiGroup.path != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}
