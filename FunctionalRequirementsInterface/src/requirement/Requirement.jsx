import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './Requirement.css';
import Button from '../common-components/button/Button';

export default function Requirement() {
    const [nps, setNps] = useState('');
    const [rnu, setRnu] = useState('');
    const [ca, setCa] = useState('');
    const [reason, setReason] = useState('');
    const [author, setAuthor] = useState('');
    const [id, setId] = useState('');
    const [creationDate, setCreationDate] = useState('');
    const [realizationDate, setRealizationDate] = useState('');

    const { id: requirementId } = useParams();

    useEffect(() => {
        const fetchRequirement = async () => {
            try {
                const response = await fetch(`http://localhost:8080/requirementsSearch/getFunctionalRequirementById/${requirementId}`);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                console.log(data);
                setId(data.id);
                setNps(data.ops);
                setRnu(data.rnu);
                setCa(data.ca);
                setReason(data.reason);
                setAuthor(data.author);
                setCreationDate(data.creationDate);
                setRealizationDate(data.realizationDate);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchRequirement();
    }, [requirementId]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!nps || !rnu || !ca || !reason || !author) {
            alert('Все поля, кроме Даты реализации, должны быть заполнены!');
            return;
        }

        const requirementToUpdate = {
            id,
            creationDate,
            ops: nps,
            rnu,
            ca,
            reason,
            realizationDate: realizationDate || null,
            author
        };

        console.log(requirementToUpdate);

        try {
            const response = await fetch('http://localhost:8080/requirements/updateExistingFunctionalRequirement', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requirementToUpdate)
            });

            if (response.ok) {
                window.location.href = '/';
            } else {
                alert('Ошибка при обновлении требования на сервере.');
            }
        } catch (error) {
            console.error('Ошибка при отправке данных:', error);
            alert('Произошла ошибка при отправке данных на сервер.');
        }
    };

    const handleRealizationDateChange = (e) => {
        setRealizationDate(e.target.value);
    };

    return (
        <div className="form-container">
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>id ФТ</label>
                    <input type="text" value={id} readOnly />
                </div>
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
                <div className="form-group">
                    <label>Дата создания</label>
                    <input type="text" value={creationDate} readOnly />
                </div>
                <div className="form-group">
                    <label>Дата реализации</label>
                    <input type="date" value={realizationDate || ''} onChange={handleRealizationDateChange} />
                </div>
                <Button onClickFunctionHandler={handleSubmit} label="Обновить">Обновить</Button>
                <Button>Форма для печати</Button>
            </form>
        </div>
    );
}
