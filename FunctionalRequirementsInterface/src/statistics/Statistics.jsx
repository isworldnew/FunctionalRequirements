import React, { useEffect, useState } from 'react';
import './Statistics.css';

export default function Statistics() {
    const [statistics, setStatistics] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('http://localhost:8080/requirementsSearch/getReasonStatistics');
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setStatistics(data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="table-container">
            <table className="styled-table">
                <thead>
                    <tr>
                        <th>Количество</th>
                        <th>Основание</th>
                    </tr>
                </thead>
                <tbody>
                    {statistics.map((stat, index) => (
                        <tr key={index}>
                            <td>{stat.amount}</td>
                            <td>{stat.reason}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
