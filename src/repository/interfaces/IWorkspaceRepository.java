package repository.interfaces;

import model.Workspace;

import java.util.List;

public interface IWorkspaceRepository {
    List<Workspace> findAll();
    boolean existsById(int id);
}
