import React from 'react';
import './CustomButton.css';

type CustomButtonProps = {
    isActive: boolean;
    callback: () => any;
}

const CustomButton: React.FC<CustomButtonProps> = ({isActive, callback}) => {
    return (
        <button className={isActive ? "button-46" : "button-46__inactive"}
                onClick={callback}>
            Select all
        </button>
    );
};

export default CustomButton;