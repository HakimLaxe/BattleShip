import React from 'react';
import './MessageBox.css'

const MessageBox = ({ message }) => {
    return (
        <div className="message-box">{message}</div>
    );
}
export default MessageBox;