

const StepForgetPassword: React.FC = () => {
    return (
        <div className="mt-8 w-full max-w-xs">
                <div className="flex items-center mb-4">
                    <div className="h-1 flex-1 bg-white bg-opacity-30 rounded-full"></div>
                    <div className="px-4 text-sm opacity-80 text-white">Steps</div>
                    <div className="h-1 flex-1 bg-white bg-opacity-30 rounded-full"></div>
                </div>
                <div className="flex justify-between text-xs text-white opacity-50">
                    <div className="flex flex-col items-center">
                        <div className="w-8 h-8 text-xl font-bold rounded-full bg-white bg-opacity-10 flex items-center justify-center mb-1">1</div>
                        <span>Verify</span>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className="w-8 h-8 text-xl font-bold rounded-full bg-white bg-opacity-10 flex items-center justify-center mb-1">2</div>
                        <span>Reset</span>
                    </div>
                    <div className="flex flex-col items-center">
                        <div className="w-8 h-8 text-xl font-bold rounded-full bg-white bg-opacity-10 flex items-center justify-center mb-1">3</div>
                        <span>Complete</span>
                    </div>
                </div>
        </div>
    );
}

export default StepForgetPassword;