import React from 'react';
import Loader from "./Loader";

const FormLoader = () => {
    return (
        <div className="h-[500px] w-[700px] flex items-center justify-center">
            <div className="w-full flex items-center justify-center">
                <Loader/>
            </div>
        </div>
    );
};

export default FormLoader;