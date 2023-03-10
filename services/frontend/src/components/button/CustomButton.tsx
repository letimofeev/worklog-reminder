import React from 'react';
import './CustomButton.css';

type CustomButtonProps = {
    isActive: boolean;
}

const CustomButton: React.FC<CustomButtonProps> = ({isActive}) => {
    return (
        <button className={isActive ? "button-46" : "button-46__inactive"}>
            Select all
        </button>
    );
};

export default CustomButton;