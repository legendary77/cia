/*
 * Copyright 2010 James Pether Sörling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
 */
package com.hack23.cia.service.data.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.CacheMode;
import org.springframework.stereotype.Repository;

import com.hack23.cia.service.data.api.DataViewer;
import com.hack23.cia.service.data.impl.util.LoadHelper;

/**
 * The Class DataViewerImpl.
 */

@Repository(value="DataViewer")
public final class DataViewerImpl implements DataViewer {

	/** The entity manager. */
	@PersistenceContext(name = "ciaPersistenceUnit")
	private EntityManager entityManager;

	/** The criteria builder. */
	private CriteriaBuilder criteriaBuilder;

	/**
	 * Adds the cache hints.
	 *
	 * @param typedQuery
	 *            the typed query
	 * @param comment
	 *            the comment
	 */
	private static void addCacheHints(final TypedQuery<?> typedQuery, final String comment) {
		typedQuery.setHint("org.hibernate.cacheMode", CacheMode.NORMAL);
		typedQuery.setHint("org.hibernate.cacheable", Boolean.TRUE);
		typedQuery.setHint("org.hibernate.comment", comment);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hack23.cia.service.data.api.AbstractGenericDAO#findFirstByProperty
	 * (javax.persistence.metamodel.SingularAttribute, java.lang.Object)
	 */
	/**
	 * Find first by property.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param property
	 *            the property
	 * @param value
	 *            the value
	 * @return the t
	 */
	@Override
	public <T> T findFirstByProperty(final Class<T> clazz,
			final SingularAttribute<T, ? extends Object> property, final Object value) {
		final List<T> resultList = findListByProperty(clazz,property,value);

		if (resultList.isEmpty()) {
			return null;
		} else {
			return LoadHelper.recursiveInitliaze(resultList.get(0));
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.hack23.cia.service.data.api.AbstractGenericDAO#getAll()
	 */
	/**
	 * Gets the all.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the all
	 */
	@Override
	public <T> List<T> getAll(final Class<T> clazz) {
		final CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(clazz);
		final Root<T> root = criteriaQuery.from(clazz);
		criteriaQuery.select(root);
		final TypedQuery<T> typedQuery = entityManager
				.createQuery(criteriaQuery);
		addCacheHints(typedQuery, "getAll." + clazz.getSimpleName());

		return typedQuery.getResultList();
	}

	/**
	 * Inits the.
	 */
	@PostConstruct
	private void init() {
		criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	/* (non-Javadoc)
	 * @see com.hack23.cia.service.data.api.DataViewer#load(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T load(final Class<T> clazz,final Object id) {
		return LoadHelper.recursiveInitliaze(entityManager.find(clazz, id));
	}

	/* (non-Javadoc)
	 * @see com.hack23.cia.service.data.api.DataViewer#findByQueryProperty(java.lang.Class, javax.persistence.metamodel.SingularAttribute, java.lang.Class, javax.persistence.metamodel.SingularAttribute, java.lang.Object)
	 */
	@Override
	public  <T,V> T findByQueryProperty(final Class<T> clazz,
			final SingularAttribute<T, ? extends Object> property,final Class<V> clazz2,
			final SingularAttribute<V, ? extends Object> property2, final Object value) {

		final CriteriaQuery<V> criteriaQuery = criteriaBuilder
				.createQuery(clazz2);
		final Root<V> root = criteriaQuery.from(clazz2);
		criteriaQuery.select(root);

		if (value instanceof String) {
			final Expression<String> propertyObject = (Expression<String>) root.get(property2);
			final Predicate condition = criteriaBuilder.equal(criteriaBuilder.upper(propertyObject), ((String) value).toUpperCase());
			criteriaQuery.where(condition);

		} else {
			final Predicate condition = criteriaBuilder.equal(root.get(property2), value);
			criteriaQuery.where(condition);
		}

		final TypedQuery<V> typedQuery = entityManager
				.createQuery(criteriaQuery);

		addCacheHints(typedQuery, "findByQueryProperty." + clazz.getSimpleName());


		final List<V> resultList = typedQuery.getResultList();

		if (resultList.size() >0) {
			final List<T> findListByProperty = findListByProperty(clazz,property,resultList.get(0));
			if (findListByProperty.size() > 0) {
				return LoadHelper.recursiveInitliaze(findListByProperty.get(0));
			}
		}

		return null;

	}

	/* (non-Javadoc)
	 * @see com.hack23.cia.service.data.api.DataViewer#findListByProperty(java.lang.Class, javax.persistence.metamodel.SingularAttribute, java.lang.Object)
	 */
	@Override
	public <T> List<T> findListByProperty(final Class<T> clazz,
			final SingularAttribute<T, ? extends Object> property, final Object value) {
		final CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(clazz);
		final Root<T> root = criteriaQuery.from(clazz);
		criteriaQuery.select(root);

		if (value instanceof String) {
			final Expression<String> propertyObject = (Expression<String>) root.get(property);
			final Predicate condition = criteriaBuilder.equal(criteriaBuilder.upper(propertyObject), ((String) value).toUpperCase());
			criteriaQuery.where(condition);

		} else {
			final Predicate condition = criteriaBuilder.equal(root.get(property), value);
			criteriaQuery.where(condition);
		}

		final TypedQuery<T> typedQuery = entityManager
				.createQuery(criteriaQuery);
		addCacheHints(typedQuery, "findListByProperty." + clazz.getSimpleName());

		return typedQuery.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.hack23.cia.service.data.api.DataViewer#findListByProperty(java.lang.Class, java.lang.Object[], javax.persistence.metamodel.SingularAttribute[])
	 */
	@Override
	public <T> List<T> findListByProperty(final Class<T> clazz, final Object[] values,
			final SingularAttribute<T, ? extends Object>... properties) {
		final CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(clazz);
		final Root<T> root = criteriaQuery.from(clazz);
		criteriaQuery.select(root);

		final Object value=values[0];
		final SingularAttribute<T, ? extends Object> property = properties[0];
		Predicate condition;

		condition = QueryHelper.equalsIgnoreCaseIfStringPredicate(criteriaBuilder,root, value, property);

		if (values.length > 1) {
			for (int i = 1; i < properties.length; i++) {
				final SingularAttribute<T, ? extends Object> property2 = properties[i];
				final Object value2=values[i];
				final Predicate condition2 = QueryHelper.equalsIgnoreCaseIfStringPredicate(criteriaBuilder,root, value2, property2);

				condition = criteriaBuilder.and(condition,condition2);
			}
		}


		criteriaQuery.where(condition);

		final TypedQuery<T> typedQuery = entityManager
				.createQuery(criteriaQuery);
		addCacheHints(typedQuery, "findListByProperty." + clazz.getSimpleName());

		return typedQuery.getResultList();
	}


	/* (non-Javadoc)
	 * @see com.hack23.cia.service.data.api.DataViewer#findListByEmbeddedProperty(java.lang.Class, javax.persistence.metamodel.SingularAttribute, java.lang.Class, javax.persistence.metamodel.SingularAttribute, java.lang.Object)
	 */
	@Override
	public <T, V> List<T> findListByEmbeddedProperty(final Class<T> clazz,
			final SingularAttribute<T, V> property, final Class<V> clazz2,
			final SingularAttribute<V, ? extends Object> property2, final Object value) {

		final CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(clazz);
		final Root<T> root = criteriaQuery.from(clazz);
		criteriaQuery.select(root);

		final Join<T, V> join = root.join(property);

		final Path<? extends Object> path = join.get(property2);

		final Predicate condition = criteriaBuilder.equal(path, value);

		criteriaQuery.where(condition);

		final TypedQuery<T> typedQuery = entityManager
				.createQuery(criteriaQuery);

		addCacheHints(typedQuery, "findListByEmbeddedProperty." + clazz.getSimpleName());


		return typedQuery.getResultList();
	}

}
