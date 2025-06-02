import LogoForgetPassword from "../../components/LogoForgetPassword";
import "@/assets/css/ForgetPasswordStyle.css";
import { useState } from "react";
import ReCAPTCHA from "react-google-recaptcha";
import EmailInput from "../../components/EmailInput";
import { RECAPTCHA_SITE_KEY } from "../../constant/recaptcha";
import { EMAIL_REGEX_PATTERN } from "../../constant/regex";
import { forgotPassword } from "../../constant/api";

const ForgetPasswordPage: React.FC = () => {
  const [email, setEmail] = useState("");
  const [errorEmail, setErrorEmail] = useState(false);
  const [recaptchaToken, setRecaptchaToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleCaptchaChange = (token: string | null) => {
    setRecaptchaToken(token);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!EMAIL_REGEX_PATTERN.test(email)) {
      setErrorEmail(true);
      return;
    }

    if (!recaptchaToken) {
      return;
    }

    setLoading(true);
    try {
      const data = await forgotPassword({ email, recaptchaToken }); // nếu không có await thì trả về là Promise<any> dẫn đến sai lệch dữ liệu không unbox ra để lấy được dữ liệu và phải dùng trong hàm async
      if (data.statusCode == 200) {
        alert("A password reset link has been sent to your email.");
      } else {
        alert("Failed to send reset password's email. Please try again.");
      }
    } catch (error) {
      alert("An error occurred. Please try again later.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-gray-100 min-h-screen flex items-center justify-center p-4">
      <div className="max-w-4xl w-full bg-white rounded-2xl shadow-xl overflow-hidden flex flex-col md:flex-row">
        <LogoForgetPassword />
      </div>
      <div className="w-full md:w-1/2 p-8 md:p-10 flex flex-col justify-center">
        <div className="mb-8">
          <h1 className="text-2xl font-bold text-gray-800 mb-2">
            Forgot Password?
          </h1>
          <p className="text-gray-600">
            Enter your email and we'll send you a link to reset your password.
          </p>
        </div>

        <form id="recoveryForm" className="space-y-6" onSubmit={handleSubmit}>
          <div className="space-y-4">
            <EmailInput
              email={email}
              OnChangeEmail={(e) => setEmail(e.target.value)}
              error={errorEmail}
            />
            <div className="bg-gray-50 p-4 rounded-lg border border-gray-200">
              <ReCAPTCHA
                sitekey={RECAPTCHA_SITE_KEY}
                onChange={handleCaptchaChange}
                size="invisible"
              />
            </div>
          </div>
          <div>
            <button
              type="submit"
              disabled={loading}
              className={`w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white 
    ${
      loading
        ? "bg-gray-400 cursor-not-allowed"
        : "bg-indigo-600 hover:bg-indigo-700"
    } 
    focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200`}
            >
              {loading ? "Submitting..." : "Submit"}
            </button>
          </div>
        </form>

        <div className="mt-6">
          <div className="relative">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-gray-300"></div>
            </div>
            <div className="relative flex justify-center text-sm">
              <span className="px-2 bg-white text-gray-500">
                {" "}
                Or try another way{" "}
              </span>
            </div>
          </div>

          <div className="mt-6 grid grid-cols-2 gap-3">
            <div>
              <button
                type="button"
                className="w-full inline-flex items-center justify-center py-2 px-4 border border-gray-300 rounded-lg shadow-sm bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200"
              >
                <i className="fab fa-google text-red-500 mr-2"></i> Google
              </button>
            </div>
            <div>
              <button
                type="button"
                className="w-full inline-flex justify-center items-center py-2 px-4 border border-gray-300 rounded-lg shadow-sm bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200"
              >
                <i className="fas fa-mobile-alt text-indigo-500 mr-2"></i> SMS
              </button>
            </div>
          </div>
        </div>
        <div className="mt-8 text-center">
          <a
            href="#"
            className="text-sm font-medium text-indigo-600 hover:text-indigo-500 transition duration-200"
          >
            <i className="fas fa-arrow-left mr-1"></i> Back to login
          </a>
        </div>
      </div>
    </div>
  );
};

export default ForgetPasswordPage;
