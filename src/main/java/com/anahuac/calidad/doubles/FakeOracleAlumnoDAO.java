package com.anahuac.calidad.doubles;

public class FakeOracleAlumnoDAO implements AlumnoDAO {

	@Override
	public boolean addAlumno(Alumno a) {
		return false;
		// TODO Auto-generated method stub
		//return true;
	}

	@Override
	public boolean deleteAlumno(Alumno a) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean updateEmail(Alumno a) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public Alumno consultarAlumno(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
