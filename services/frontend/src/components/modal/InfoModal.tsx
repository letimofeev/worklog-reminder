import React from 'react';
import classes from './InfoModal.module.css';

interface InfoModalProps {
    children: any;
    visible: boolean;
    setVisible: any;
}

const InfoModal: React.FC<InfoModalProps> = ({children, visible, setVisible}) => {
    const rootClasses = [classes.infoModal]
    if (visible) {
        rootClasses.push(classes.active)
    }

    return (
        <div className={rootClasses.join(' ')} onClick={() => setVisible(false)}>
            <div className={classes.infoModalContent} onClick={(e) => e.stopPropagation()}>
                {children}
            </div>
        </div>
    );
};

export default InfoModal;