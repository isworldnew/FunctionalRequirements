import React, { useState } from 'react';
import './NewRequirement.css';
import Button from '../common-components/button/Button';

export default function NewRequirement() {
    const [nps, setNps] = useState('');
    const [rnu, setRnu] = useState('');
    const [ca, setCa] = useState('');
    const [reason, setReason] = useState('');
    const [author, setAuthor] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        
        if (!nps || !rnu || !ca || !reason || !author) {
            alert('Все поля должны быть заполнены!');
            return;
        }

        const requirement = {
            author,
            rnu,
            ops: nps,
            ca,
            reason
        };

        console.log(requirement);

        try {
            const response = await fetch('http://localhost:8080/requirements/saveNewFunctionalRequirement', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requirement)
            });

            if (response.ok) {
                window.location.href = '/';
            } else {
                alert('Ошибка при отправке данных на сервер.');
            }
        } catch (error) {
            console.error('Ошибка при отправке данных:', error);
            alert('Произошла ошибка при отправке данных на сервер.');
        }
    };

    return (
        <div className="form-container">
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>НПС</label>
                    <textarea value={nps} onChange={(e) => setNps(e.target.value)} />
                </div>
                <div className="form-group">
                    <label>РНУ</label>
                    <textarea value={rnu} onChange={(e) => setRnu(e.target.value)} />
                </div>
                <div className="form-group">
                    <label>СА</label>
                    <textarea value={ca} onChange={(e) => setCa(e.target.value)} />
                </div>
                <div className="form-group">
                    <label>Основание</label>
                    <textarea value={reason} onChange={(e) => setReason(e.target.value)} />
                </div>
                <div className="form-group">
                    <label>Автор</label>
                    <textarea value={author} onChange={(e) => setAuthor(e.target.value)} />
                </div>
                <Button onClickFunctionHandler={handleSubmit} label="Добавить">Добавить</Button>
            </form>
        </div>
    );
}
