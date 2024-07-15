import React, { useEffect, useState } from 'react';
import './Requirements.css';
import Button from '../common-components/button/Button';

export default function Requirements() {
    const [requirements, setRequirements] = useState([]);
    const [filterCriteria, setFilterCriteria] = useState('id ФТ');
    const [searchText, setSearchText] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('http://localhost:8080/requirements/getAllFunctionalRequirements');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setRequirements(data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    const handleRowClick = (id) => {
        window.location.href = `/requirement/${id}`;
    };

    const newFRclick = () => {
        window.location.href = '/new-functional-requirement';
    };

    const statisticsClick = () => {
        window.location.href = '/statistics';
    };

    const handleFilterCriteriaChange = (event) => {
        setFilterCriteria(event.target.value);
    };

    const handleSearchInputChange = (event) => {
        setSearchText(event.target.value);
    };

    const handleSearchClick = async () => {
        let url = '';

        switch (filterCriteria) {
            case 'id ФТ':
                url = `http://localhost:8080/requirementsSearch/getFunctionalRequirementById/${searchText}`;
                break;
            case 'РНУ':
                url = `http://localhost:8080/requirementsSearch/getAllRequirementsByRnu/${searchText}`;
                break;
            case 'НПС':
                url = `http://localhost:8080/requirementsSearch/getAllRequirementsByOps/${searchText}`;
                break;
            case 'СА':
                url = `http://localhost:8080/requirementsSearch/getAllRequirementsByCa/${searchText}`;
                break;
            case 'Основание':
                url = `http://localhost:8080/requirementsSearch/getAllRequirementsByReason/${searchText}`;
                break;
            case 'Автор':
                url = `http://localhost:8080/requirementsSearch/getAllRequirementsByAuthor/${searchText}`;
                break;
            default:
                return;
        }

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            if (Array.isArray(data)) {
                setRequirements(data);
            } else {
                setRequirements([data]);
            }
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    return (
        <>
            <div className="buttons">
                <Button onClickFunctionHandler={newFRclick}>Новое ФТ</Button>
                <Button onClickFunctionHandler={statisticsClick}>Статистика</Button>
            </div>
            <div className="filter-container">
                <select value={filterCriteria} onChange={handleFilterCriteriaChange}>
                    <option value="id ФТ">id ФТ</option>
                    <option value="РНУ">РНУ</option>
                    <option value="НПС">НПС</option>
                    <option value="СА">СА</option>
                    <option value="Основание">Основание</option>
                    <option value="Автор">Автор</option>
                    <option value="Дата создания">Дата создания</option>
                    <option value="Дата реализации">Дата реализации</option>
                </select>
                <input type="text" value={searchText} onChange={handleSearchInputChange} placeholder="Введите значение для поиска" />
                <button onClick={handleSearchClick}>Поиск</button>
            </div>
            <div className="table-container">
                <table className="styled-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>id ФТ</th>
                            <th>РНУ</th>
                            <th>НПС</th>
                            <th>СА</th>
                            <th>Основание</th>
                            <th>Дата создания</th>
                            <th>Дата реализации</th>
                            <th>Автор</th>
                        </tr>
                    </thead>
                    <tbody>
                        {requirements.map((requirement, index) => (
                            <tr key={requirement.id} onClick={() => handleRowClick(requirement.id)}>
                                <td>{index + 1}</td>
                                <td>{requirement.id}</td>
                                <td>{requirement.rnu}</td>
                                <td>{requirement.ops}</td>
                                <td>{requirement.ca}</td>
                                <td>{requirement.reason}</td>
                                <td>{requirement.creationDate}</td>
                                <td>{requirement.realizationDate || ''}</td>
                                <td>{requirement.author}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </>
    );
}
