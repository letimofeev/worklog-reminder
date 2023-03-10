import React from 'react';
import './RoundCheckbox.css';

type CheckboxProps = {
    rowNumber: number
    handleCheckboxChange: (index: number) => void;
    checked: boolean;
}

const RoundCheckbox: React.FC<CheckboxProps> = ({rowNumber, handleCheckboxChange, checked}) => {
    return (
        <div className="checkbox-wrapper-31">
            <input type="checkbox"
                   onChange={() => handleCheckboxChange(rowNumber - 1)}
                   checked={checked}
            />
            <svg viewBox="0 0 35.6 35.6">
                <circle className="background" cx="17.8" cy="17.8" r="17.8"></circle>
                <circle className="stroke" cx="17.8" cy="17.8" r="14.37"></circle>
                <polyline className="check" points="11.78 18.12 15.55 22.23 25.17 12.87"></polyline>
            </svg>
        </div>
    );
};

export default RoundCheckbox;
