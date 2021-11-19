package com.anahuac.calidad.dbunit;

import static org.junit.Assert.*;

import java.io.File;

import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.anahuac.calidad.dbunit.AlumnoDAOmysql;
import com.anahuac.calidad.doubles.Alumno;

import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class DAOAlumnoTest extends DBTestCase{
	
	public DAOAlumnoTest() {
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:33060/pruebas_db");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "secret");
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception{
		return new FlatXmlDataSetBuilder().build(new File("src/resources/iniDB.xml"));
	}

	@Before
	public void setUp() throws Exception {
		IDatabaseConnection connection = getConnection();
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection,getDataSet());
		}catch(Exception e) {
			fail("Error in setup: " + e.getMessage());
		}finally {
			connection.close();
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddAlumno() {
		Alumno alumno = new Alumno("alumno1","4",23,"hola@gmail.com");
		AlumnoDAOmysql daoMySQL = new AlumnoDAOmysql();
		
		daoMySQL.addAlumno(alumno);
		
		try {
			IDataSet databaseDataSet = getConnection().createDataSet();
			
			ITable actualTable = databaseDataSet.getTable("alumnos_tbl");
			
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/insert_result.xml"));
			ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");
			
			Assertion.assertEquals(expectedTable, actualTable);
		}catch(Exception e) {
			fail("Error in insert Ttest: " + e.getMessage());
		}
		
	}
	
	@Test
	public void testDeleteAlumno() {
		//<alumnos_tbl id="003" nombre="hola3" email="hola@hola.com" edad = "20"/>
		Alumno alumno = new Alumno("hola3","003",20,"hola@hola.com");
		AlumnoDAOmysql daoMySQL = new AlumnoDAOmysql();
		
		daoMySQL.deleteAlumno(alumno);
		
		try {
			IDataSet databaseDataSet = getConnection().createDataSet();
			
			ITable actualTable = databaseDataSet.getTable("alumnos_tbl");
			
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/delete_result.xml"));
			ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");
			
			Assertion.assertEquals(expectedTable, actualTable);
		}catch(Exception e) {
			fail("Error in insert Ttest: " + e.getMessage());
		}
		
	}
	
	@Test
	public void testUpdateEmailAlumno() {
		Alumno alumno = new Alumno("hola3","003",20,"hola@gmail.com");;
		AlumnoDAOmysql daoMySQL = new AlumnoDAOmysql();
		
		daoMySQL.updateEmail(alumno);
		
		try {
			IDataSet databaseDataSet = getConnection().createDataSet();
			
			ITable actualTable = databaseDataSet.getTable("alumnos_tbl");
			
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/updateEmail_result.xml"));
			ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");
			
			Assertion.assertEquals(expectedTable, actualTable);
		}catch(Exception e) {
			fail("Error in insert Ttest: " + e.getMessage());
		}
		
	}
	
	@Test
	public void testConsultarAlumno() {
		//String id = "003";
		Alumno alumnoExpected = new Alumno("hola3","003",20,"hola@hola.com");;
		AlumnoDAOmysql daoMySQL = new AlumnoDAOmysql();
		
		Alumno alumnoActual = daoMySQL.consultarAlumno(alumnoExpected.getId());
		
		try {
			assertThat(alumnoExpected.getNombre(),is(alumnoActual.getNombre()));
			assertThat(alumnoExpected.getId(),is(alumnoActual.getId()));
			assertThat(alumnoExpected.getEdad(),is(alumnoActual.getEdad()));
			assertThat(alumnoExpected.getEmail(),is(alumnoActual.getEmail()));
		}catch(Exception e) {
			fail("Error in insert Ttest: " + e.getMessage());
		}
		
	}
}
