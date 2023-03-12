import React, {CSSProperties} from 'react';
import './FailIcon.scss';

type FailIconProps = {
    style?: CSSProperties;
}

const FailIcon: React.FC<FailIconProps> = ({style}) => {
    return (
        <div className="swal2-icon swal2-error swal2-animate-error-icon"
             style={
                 {
                     display: 'flex',
                     marginTop: '8px',
                     ...style
                 }}
        >
            <span
                className="swal2-x-mark"><span className="swal2-x-mark-line-left"></span>
                <span className="swal2-x-mark-line-right"></span>
            </span>
        </div>

    );
};

export default FailIcon;
