import React from 'react';
import './Button.css';

export default function Button({ onClickFunctionHandler, children }) {
    return (
        <button className="styled-button" onClick={onClickFunctionHandler}>
            {children}
        </button>
    );
}
