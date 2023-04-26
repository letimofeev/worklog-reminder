import React from 'react';
import classes from './customModal.module.css';

interface CustomModalProps {
    children: any;
    visible: boolean;
    onClose: () => void;
}

const CustomModal: React.FC<CustomModalProps> = ({children, visible, onClose}) => {
    const rootClasses = [classes.customModal]
    if (visible) {
        rootClasses.push(classes.active)
    }

    return (
        <div className={rootClasses.join(' ')} onClick={onClose}>
            <div className={classes.customModalContent} onClick={(e) => e.stopPropagation()}>
                {children}
            </div>
        </div>
    );
};

export default CustomModal;
