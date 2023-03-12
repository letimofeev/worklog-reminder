import React, {CSSProperties} from 'react';
import './SuccessIcon.scss';

type SuccessIconProps = {
    style?: CSSProperties;
}

const SuccessIcon: React.FC<SuccessIconProps> = ({style}) => {
    return (
        <div className="success-icon" style={style}>
            <div className="success-icon__tip"></div>
            <div className="success-icon__long"></div>
        </div>
    );
};

export default SuccessIcon;