<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список приёмов пищи</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <style type="text/css">
        table.meals {
            border-collapse: collapse;
        }

        table.meals td,
        table.meals th {
            border: 1px solid black;
            padding: 5px;
        }

        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>

<form method="post" action="users">

    Текущий пользователь:
    <select name="user" value="${user}">

        <c:forEach var="u" items="${userList}">
            <option value="${u.id}" <c:out value="${user == u.id ? 'selected' : ''}"></c:out>>
                <c:out value="${u.name}"></c:out>
            </option>
        </c:forEach>
    </select>
    <button type="submit">Выбрать</button>
</form>


<form action="<c:url value="/meals"/>" method="post">

    <fieldset>
        <legend>Фильтрация данных:</legend>
        Дата, с:
        <input name="startDate" value="${startDate}">
        по:
        <input name="endDate" value="${endDate}">

        <p/>
        Время, с:
        <input name="startTime" value="${startTime}">
        по:
        <input name="endTime" value="${endTime}">

        <p/>
        <button type="submit">Фильтровать</button>
    </fieldset>

    <fieldset>
        <legend>Основные данные:</legend>
        <a href="meals?action=create">Добавить прием пищи</a>
        </p>
        <table class="meals">
            <tr>
                <th>Дата и время</th>
                <th>Описание</th>
                <th>Калории</th>
                <th></th>
                <th></th>
            </tr>

            <c:forEach var="meal" items="${mealList}">
                <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                    <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate" type="date"/>
                    <td><fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td><c:out value="${meal.description}"></c:out></td>
                    <td><c:out value="${meal.calories}"></c:out></td>
                    <td><a href="meals?action=update&id=${meal.id}">Изменить</a></td>
                    <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
                </tr>
            </c:forEach>
        </table>
    </fieldset>
</form>
</body>
</html>