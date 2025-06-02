
const LogoForgetPassword: React.FC = () => {
    return(
        <div className="text-center text-white">
            <div className="w-32 h-32 mx-auto mb-6 relative">
                <div className="absolute inset-0 bg-white bg-opacity-20 rounded-full animate-pulse"></div>
                <div className="absolute inset-4 bg-white bg-opacity-30 rounded-full animate-ping"></div>
                <i className="fas fa-comments text-5xl absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"></i>
            </div>
            <h2 className="text-2xl font-bold mb-2">TalkNest</h2>
            <p className="opacity-90">Enjoy your chatting</p>
        </div>
    )
}

export default LogoForgetPassword;


