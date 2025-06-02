
interface EmailInputProps {
    email: string,
    OnChangeEmail: (e: React.ChangeEvent<HTMLInputElement>) => void,
    error?: boolean
}

const EmailInput: React.FC<EmailInputProps> = ({email, OnChangeEmail, error}) => {
    return (
        <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">Email Address</label>
            <div className="relative">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <i className="fas fa-envelope text-gray-400"></i>
            </div>
            <input 
                type="email" 
                id="email" 
                name="email" 
                value={email}
                onChange={OnChangeEmail}
                required
                className={`block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg shadow-sm 
                    focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 
                    transition duration-200 ${error ? `border-red-500`: `border-gray-300`}`}
                placeholder="your@email.com"/>
            </div>
        </div>
    );
}

export default EmailInput;





