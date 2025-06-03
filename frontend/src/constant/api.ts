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

// async, await => khi bình thường giả sử application của mình có một cái pool chứa khoảng 100 threads
// vậy nếu đặt ra mỗi một yêu cầu của người dùng là 1 thread vaajay thì application của chứa chịu tải tối đa 100 request
// => bị ngu

// javascript đẻ ra async await / ,net ra async await / java => nhưng có method ví dụ hashMap HashMapConcurrency

// fetch api nếu không có IOBlock nó dừng cái ứng dụng ngồi đợi chừng nào web backend response thì thôi 
// quá tải 

// async await mỗi request đi đến be để cái yêu cầu ở đó và cái request đso trở về cái pool threads ban đầu 