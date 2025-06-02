import axios from "axios";
import type { ForgotPasswordPayload } from "../types/auth";

// gửi như dưới này là sẽ chia thêm một cấp data: {email: , recaptchaToken:} sẽ dẫn đến không map tự động sang backend được
// export const forgotPassword = async (data: ForgotPasswordPayload) => {
//     const response = await axios.post("http://localhost/8080/talknest/api/auth/forgot-password", {
//         data
//     });

//     return response.data;
// }

// đây là tiêu chuẩn dễ dàng xử lí nhất vì gửi đúng cấp {email, recaptchaToken} cho backend
// đây là response từ axios sau khi nhận dữ liệu từ backend
// {
//   data: {...},           // <-- đây là body JSON trả về từ backend
//   status: 200,           // HTTP status code
//   statusText: "OK",
//   headers: {...},        // headers response
//   config: {...},         // cấu hình request axios
//   request: {...}         // request gốc
// }

export const forgotPassword = async ({email, recaptchaToken} : ForgotPasswordPayload) => {
    const response = await axios.post("http://localhost/8080/talknest/api/auth/forgot-password", {
        email,
        recaptchaToken
    });

    return response.data;
}