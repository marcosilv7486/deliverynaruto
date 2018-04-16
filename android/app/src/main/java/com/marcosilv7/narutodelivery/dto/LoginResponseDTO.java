package com.marcosilv7.narutodelivery.dto;

public class LoginResponseDTO {

    private DataLoginResponseDTO data;

    public DataLoginResponseDTO getData() {
        return data;
    }

    public void setData(DataLoginResponseDTO data) {
        this.data = data;
    }

    public class DataLoginResponseDTO {
        private String token;
        private String refreshtoken;
        private Long userId;
        private String avatar;
        private String fullName;
        private String email;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshtoken() {
            return refreshtoken;
        }

        public void setRefreshtoken(String refreshtoken) {
            this.refreshtoken = refreshtoken;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
