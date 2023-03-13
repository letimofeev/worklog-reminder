import React, {CSSProperties} from 'react';
import {FaExclamationCircle} from "react-icons/fa";
import './ErrorBlock.css';

type ErrorBlockProps = {
    header: string;
    body: string;
    style?: CSSProperties;
}

const ErrorBlock: React.FC<ErrorBlockProps> = ({header, body, style}) => {
    return (
        <div className="error-alert" style={style}>
            <div className="error-header">
                <i>
                    <FaExclamationCircle/>
                </i>
                <h3>{header}</h3>
            </div>
            <div className="error-message">
                <p>{body}</p>
            </div>
        </div>
    );
};

export default ErrorBlock;