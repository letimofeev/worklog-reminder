import React from 'react';
import classes from './Checkbox.module.css';

type CheckboxProps = {
    rowNumber: number
    handleCheckboxChange: (index: number) => void;
}

const Checkbox: React.FC<CheckboxProps> = ({rowNumber, handleCheckboxChange}) => {
    return (
        <div className={classes.checkbox}>
            <input
                type="checkbox"
                onChange={() => handleCheckboxChange(rowNumber - 1)}
            />
        </div>
    );
};

export default Checkbox;