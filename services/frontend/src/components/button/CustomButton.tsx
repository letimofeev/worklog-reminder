import React from 'react';
import './CustomButton.css';

type CustomButtonProps = {
    text: string;
    isActive: boolean;
    callback: () => any;
}

const CustomButton: React.FC<CustomButtonProps> = ({text, isActive, callback}) => {
    return (
        <button className={isActive ? "button-46" : "button-46__inactive"}
                onClick={callback}>
            {text}
        </button>
    );
};

export default CustomButton;