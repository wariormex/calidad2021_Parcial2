package com.anahuac.calidad.doubles;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.anahuac.calidad.doubles.Alumno;
import com.anahuac.calidad.doubles.FakeOracleAlumnoDAO;

public class FakeOracleAlumnoDAOTest {
	
	private FakeOracleAlumnoDAO dao;
	private HashMap<String, Alumno> alumnos;
	Alumno alumno1;

	@Before
	public void setUp() throws Exception {
		dao = Mockito.mock(FakeOracleAlumnoDAO.class);
		alumnos = new HashMap<String, Alumno>();
		alumno1 = new Alumno("Nombre", "001", 20, "micorreo@hola.com");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addAlumnoTest() {
		int cuantosAntes = alumnos.size();
		System.out.println("Alumnos antes: " + cuantosAntes);
		//Alumno alumno1 = new Alumno("Nombre", "001", 20, "micorreo@hola.com");
		/*
		when(dao.addAlumno(any(Alumno.class))).thenAnswer(
				new Answer<Boolean>() {
					public Boolean answer(InvocationOnMock invocation) throws Throwable{
						Alumno arg = (Alumno) invocation.getArguments()[0];
						alumnos.put(anyString(),arg);
						System.out.println("Alumnos put: " + alumnos.size());
						return true;
			}
		}
		);
		*/
		
		doAnswer(new Answer() {
		      public Object answer(InvocationOnMock invocation) {
		    	  Alumno arg = (Alumno) invocation.getArguments()[0];
					alumnos.put(anyString(), arg);
		          return null;
		      }})
		  .when(dao).addAlumno(any(Alumno.class));
		
		dao.addAlumno(alumno1);
		int cuantosDesp = alumnos.size();
		System.out.println("Alumnos despues: " + cuantosDesp);
		assertThat(cuantosAntes+1,is(cuantosDesp));
	}
	
	@Test
	public void deleteAlumnoTest() {
		//Alumno alumno1 = new Alumno("Nombre", "001", 20, "micorreo@hola.com");
		alumnos.put(alumno1.getId(), alumno1);
		int cuantosAntes = alumnos.size();
		System.out.println("Alumnos antes: " + cuantosAntes);
		
		doAnswer(new Answer() {
		      public Object answer(InvocationOnMock invocation) {
		    	  Alumno arg = (Alumno) invocation.getArguments()[0];
					alumnos.remove(arg.getId(),arg);
		          return null;
		      }})
		  .when(dao).deleteAlumno(any(Alumno.class));
		
		dao.deleteAlumno(alumno1);
		int cuantosDesp = alumnos.size();
		System.out.println("Alumnos despues: " + cuantosDesp);
		assertThat(cuantosAntes-1,is(cuantosDesp));
	}
	
	@Test
	public void updateEmailTest() {
		//Alumno alumno1 = new Alumno("Nombre", "001", 20, "micorreo@hola.com");
		alumnos.put(alumno1.getId(), alumno1);
		String correoAntes = alumno1.getEmail();
		System.out.println("Correo antes: " + correoAntes);
		//alumno1 = new Alumno("Nombre", "001", 20, "micorreo@hola.com");
		
		doAnswer(new Answer() {
		      public Object answer(InvocationOnMock invocation) {
		    	  Alumno arg = (Alumno) invocation.getArguments()[0];
					alumnos.get(arg.getId()).setEmail("micorreo2@hola.com");
		          return null;
		      }})
		  .when(dao).updateEmail(any(Alumno.class));
		
		dao.updateEmail(alumno1);
		String correoDesp = alumnos.get(alumno1.getId()).getEmail();
		System.out.println("Correo despues: " + correoDesp);
		assertThat(correoAntes,is(not(correoDesp)));
	}
	
	@Test
	public void consultarAlumnoTest() {
		//Alumno alumno1 = new Alumno("Nombre", "001", 20, "micorreo@hola.com");
		alumnos.put(alumno1.getId(), alumno1);
		Alumno alumnoABuscar = alumno1;
		
		doAnswer(new Answer() {
		      public Alumno answer(InvocationOnMock invocation) {
		    	  String arg = (String)invocation.getArguments()[0];
		          return alumnos.get(arg);
		      }})
		  .when(dao).consultarAlumno(any(String.class));
		
		Alumno alumnoEncontrado = dao.consultarAlumno(alumno1.getId());
		assertThat(alumnoABuscar,is(alumnoEncontrado));
	}

}
