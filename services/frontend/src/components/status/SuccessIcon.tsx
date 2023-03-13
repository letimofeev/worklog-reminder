import React, {CSSProperties} from 'react';
import './StatusIcon.scss';

type SuccessIconProps = {
    style?: CSSProperties;
}

const SuccessIcon: React.FC<SuccessIconProps> = ({style}) => {
    return (
        <div className="swal2-icon swal2-success swal2-animate-success-icon" style={
            {
                display: 'flex',
                ...style
            }
        }>
            <span className="swal2-success-line-tip"></span>
            <span className="swal2-success-line-long"></span>
            <div className="swal2-success-ring"></div>
        </div>
    );
};

export default SuccessIcon;