package ru.bulkashmak.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String login;
    private String password;
    private Role role;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User)) return false;
        User user = (User) object;
        return login.equals(user.login) && password.equals(user.password) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Data
    @AllArgsConstructor
    public static class Role {

        private Integer id;
        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Role)) return false;
            Role role = (Role) o;
            return name.equals(role.name);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + name.hashCode();
            return result;
        }
    }
}