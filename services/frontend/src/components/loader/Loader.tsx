import React, {CSSProperties} from 'react';
import classes from './Loader.module.css'

type LoaderProps = {
    style?: CSSProperties;
}

const Loader: React.FC<LoaderProps> = ({style}) => {
    return (
        <div className={classes.loader}
             style={style}
        />
    );
};

export default Loader;